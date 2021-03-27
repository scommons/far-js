package farjs.filelist.copy

import farjs.filelist.api.FileListItem
import farjs.ui._
import farjs.ui.border._
import farjs.ui.popup._
import farjs.ui.theme.Theme
import scommons.react._
import scommons.react.blessed._

import scala.scalajs.js

case class FileExistsPopupProps(newItem: FileListItem,
                                existing: FileListItem,
                                onAction: FileExistsAction => Unit,
                                onCancel: () => Unit)

object FileExistsPopup extends FunctionComponent[FileExistsPopupProps] {
  
  private[copy] var popupComp: UiComponent[PopupProps] = Popup
  private[copy] var doubleBorderComp: UiComponent[DoubleBorderProps] = DoubleBorder
  private[copy] var textLineComp: UiComponent[TextLineProps] = TextLine
  private[copy] var horizontalLineComp: UiComponent[HorizontalLineProps] = HorizontalLine

  protected def render(compProps: Props): ReactElement = {
    val props = compProps.wrapped
    val (width, height) = (58, 11)
    val theme = Theme.current.popup.error

    def onButton(action: FileExistsAction) = { () =>
      props.onAction(action)
    }

    val buttons = List(
      " Overwrite " -> onButton(FileExistsAction.Overwrite),
      " All " -> onButton(FileExistsAction.All),
      " Skip " -> onButton(FileExistsAction.Skip),
      " Skip all " -> onButton(FileExistsAction.SkipAll),
      " Append " -> onButton(FileExistsAction.Append),
      " Cancel " -> props.onCancel
    ).foldLeft(List.empty[(String, () => Unit, Int)]) {
      case (result, (label, action)) =>
        val nextPos = result match {
          case Nil => 0
          case (content, _, pos) :: _ => pos + content.length
        }
        (label, action, nextPos) :: result
    }.reverse.map {
      case (content, onAction, pos) => (content.length, <.button(
        ^.key := s"$pos",
        ^.rbMouse := true,
        ^.rbWidth := content.length,
        ^.rbHeight := 1,
        ^.rbLeft := pos,
        ^.rbStyle := theme,
        ^.rbOnPress := onAction,
        ^.content := content
      )())
    }

    <(popupComp())(^.wrapped := PopupProps(onClose = props.onCancel))(
      <.box(
        ^.rbClickable := true,
        ^.rbAutoFocus := false,
        ^.rbWidth := width,
        ^.rbHeight := height,
        ^.rbTop := "center",
        ^.rbLeft := "center",
        ^.rbShadow := true,
        ^.rbStyle := theme
      )(
        <(doubleBorderComp())(^.wrapped := DoubleBorderProps(
          size = (width - 6, height - 2),
          style = theme,
          pos = (3, 1),
          title = Some("Warning")
        ))(),

        <.text(
          ^.rbLeft := "center",
          ^.rbTop := 2,
          ^.rbStyle := theme,
          ^.content := "File already exists"
        )(),
        <(textLineComp())(^.wrapped := TextLineProps(
          align = TextLine.Center,
          pos = (5, 3),
          width = width - 10,
          text = props.newItem.name,
          style = theme,
          padding = 0
        ))(),
        
        <(horizontalLineComp())(^.wrapped := HorizontalLineProps(
          pos = (3, 4),
          length = width - 6,
          lineCh = SingleBorder.horizontalCh,
          style = theme,
          startCh = Some(DoubleBorder.leftSingleCh),
          endCh = Some(DoubleBorder.rightSingleCh)
        ))(),
        <.text(
          ^.rbLeft := 5,
          ^.rbTop := 5,
          ^.rbStyle := theme,
          ^.content :=
            """New
              |Existing""".stripMargin
        )(),
        <(textLineComp())(^.wrapped := TextLineProps(
          align = TextLine.Right,
          pos = (5, 5),
          width = width - 10,
          text = {
            val date = new js.Date(props.newItem.mtimeMs)
            f"${props.newItem.size}%.0f ${date.toLocaleDateString()} ${date.toLocaleTimeString()}"
          },
          style = theme,
          padding = 0
        ))(),
        <(textLineComp())(^.wrapped := TextLineProps(
          align = TextLine.Right,
          pos = (5, 6),
          width = width - 10,
          text = {
            val date = new js.Date(props.existing.mtimeMs)
            f"${props.existing.size}%.0f ${date.toLocaleDateString()} ${date.toLocaleTimeString()}"
          },
          style = theme,
          padding = 0
        ))(),
        
        <(horizontalLineComp())(^.wrapped := HorizontalLineProps(
          pos = (3, 7),
          length = width - 6,
          lineCh = SingleBorder.horizontalCh,
          style = theme,
          startCh = Some(DoubleBorder.leftSingleCh),
          endCh = Some(DoubleBorder.rightSingleCh)
        ))(),

        <.box(
          ^.rbWidth := buttons.map(_._1).sum,
          ^.rbHeight := 1,
          ^.rbLeft := 5,
          ^.rbTop := height - 3,
          ^.rbStyle := theme
        )(
          buttons.map(_._2)
        )
      )
    )
  }
}