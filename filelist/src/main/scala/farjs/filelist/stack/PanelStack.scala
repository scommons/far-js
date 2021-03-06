package farjs.filelist.stack

import farjs.filelist.stack.PanelStack.StackItem
import farjs.ui.{WithSize, WithSizeProps}
import scommons.react._
import scommons.react.blessed.BlessedElement
import scommons.react.hooks._

import scala.scalajs.js

class PanelStack(top: Option[StackItem],
                 updater: js.Function1[js.Function1[List[StackItem], List[StackItem]], Unit]) {

  def push[T](comp: ReactClass, params: T): Unit = {
    updater { stack =>
      (comp, params.asInstanceOf[js.Any]) :: stack
    }
  }

  def update[T](params: T): Unit = {
    updater { stack =>
      if (stack.nonEmpty) {
        val (comp, _) = stack.head
        (comp, params.asInstanceOf[js.Any]) :: stack.tail
      }
      else stack
    }
  }

  def pop(): Unit = {
    updater(_.tail)
  }

  def peek: Option[StackItem] = top
  
  def params[T]: T = top.map(_._2).orNull.asInstanceOf[T]
}

case class PanelStackProps(isRight: Boolean,
                           panelInput: BlessedElement,
                           stack: PanelStack = null,
                           width: Int = 0,
                           height: Int = 0)

object PanelStack extends FunctionComponent[PanelStackProps] {
  
  type StackItem = (ReactClass, js.Any)

  val Context: ReactContext[PanelStackProps] = ReactContext[PanelStackProps](defaultValue = null)

  private[stack] var withSizeComp: UiComponent[WithSizeProps] = WithSize

  def usePanelStack: PanelStackProps = {
    val ctx = useContext(Context)
    if (ctx == null) {
      throw js.JavaScriptException(js.Error(
        "PanelStack.Context is not found." +
          "\nPlease, make sure you use PanelStack and not creating nested stacks."
      ))
    }
    ctx
  }

  protected def render(compProps: Props): ReactElement = {
    val props = compProps.wrapped
    val stacks = WithPanelStacks.usePanelStacks
    val stack =
      if (props.isRight) stacks.rightStack
      else stacks.leftStack

    val maybeTop = stack.peek

    <(withSizeComp())(^.wrapped := WithSizeProps({ (width, height) =>
      <(PanelStack.Context.Provider)(^.contextValue := props.copy(stack = stack, width = width, height = height))(
        maybeTop match {
          case None => compProps.children
          case Some((comp, _)) => <(comp)()()
        }
      )
    }))()
  }
}
