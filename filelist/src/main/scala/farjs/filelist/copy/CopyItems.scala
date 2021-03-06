package farjs.filelist.copy

import farjs.filelist.FileListActions.{FileListParamsChangedAction, FileListTaskAction}
import farjs.filelist.popups.FileListPopupsActions.FileListPopupCopyItemsAction
import farjs.filelist.popups.FileListPopupsProps
import farjs.ui.popup._
import farjs.ui.theme.Theme
import scommons.react._
import scommons.react.hooks._
import scommons.react.redux.task.FutureTask

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object CopyItems extends FunctionComponent[FileListPopupsProps] {

  private[copy] var copyItemsStats: UiComponent[CopyItemsStatsProps] = CopyItemsStats
  private[copy] var copyItemsPopup: UiComponent[CopyItemsPopupProps] = CopyItemsPopup
  private[copy] var copyProcessComp: UiComponent[CopyProcessProps] = CopyProcess
  private[copy] var messageBoxComp: UiComponent[MessageBoxProps] = MessageBox

  protected def render(compProps: Props): ReactElement = {
    val (maybeTotal, setTotal) = useState[Option[Double]](None)
    val (toPath, setToPath) = useState[Option[String]](None)
    val copied = useRef(Set.empty[String])
    val props = compProps.wrapped
    
    val showPopup = props.data.popups.showCopyItemsPopup
    val (fromState, toState) =
      if (props.data.left.isActive) (props.data.left, props.data.right)
      else (props.data.right, props.data.left)

    val items =
      if (fromState.selectedItems.nonEmpty) fromState.selectedItems
      else fromState.currentItem.toList

    def onCancel(dispatchAction: Boolean): () => Unit = { () =>
      if (dispatchAction) {
        props.dispatch(FileListPopupCopyItemsAction(show = false))
      }
      setTotal(None)
      setToPath(None)
      copied.current = Set.empty[String]
    }
    
    val onDone: () => Unit = { () =>
      val updatedSelection = fromState.selectedNames -- copied.current
      if (updatedSelection != fromState.selectedNames) {
        props.dispatch(FileListParamsChangedAction(
          isRight = fromState.isRight,
          offset = fromState.offset,
          index = fromState.index,
          selectedNames = updatedSelection
        ))
      }
      
      onCancel(dispatchAction = false)()

      val leftAction = props.actions.updateDir(props.dispatch, isRight = false, props.data.left.currDir.path)
      props.dispatch(leftAction)

      leftAction.task.future.andThen {
        case Success(_) =>
          val rightAction = props.actions.updateDir(props.dispatch, isRight = true, props.data.right.currDir.path)
          props.dispatch(rightAction)
      }
    }
    
    <.>()(
      if (showPopup) Some {
        if (maybeTotal.isEmpty) {
          <(copyItemsStats())(^.wrapped := CopyItemsStatsProps(
            dispatch = props.dispatch,
            actions = props.actions,
            state = fromState,
            onDone = { total =>
              setTotal(Some(total))
            },
            onCancel = onCancel(dispatchAction = true)
          ))()
        }
        else {
          <(copyItemsPopup())(^.wrapped := CopyItemsPopupProps(
            path = toState.currDir.path,
            items = items,
            onCopy = { path =>
              val dirF = props.actions.readDir(Some(fromState.currDir.path), path)
              dirF.onComplete {
                case Success(dir) =>
                  props.dispatch(FileListPopupCopyItemsAction(show = false))
                  setToPath(Some(dir.path))
                case Failure(_) =>
                  props.dispatch(FileListTaskAction(FutureTask("Resolving target dir", dirF)))
              }
            },
            onCancel = onCancel(dispatchAction = true)
          ))()
        }
      }
      else None,

      for {
        to <- toPath
        total <- maybeTotal
      } yield {
        if (fromState.currDir.path == to) {
          <(messageBoxComp())(^.wrapped := MessageBoxProps(
            title = "Error",
            message = s"Cannot copy the item\n${items.head.name}\nonto itself",
            actions = List(MessageBoxAction.OK(onCancel(dispatchAction = false))),
            style = Theme.current.popup.error
          ))()
        }
        else {
          <(copyProcessComp())(^.wrapped := CopyProcessProps(
            dispatch = props.dispatch,
            actions = props.actions,
            fromPath = fromState.currDir.path,
            items = items,
            toPath = to,
            total = total,
            onTopItem = { item =>
              copied.current += item.name
            },
            onDone = onDone
          ))()
        }
      }
    )
  }
}
