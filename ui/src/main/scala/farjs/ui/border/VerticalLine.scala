package farjs.ui.border

import scommons.react._
import scommons.react.blessed._

case class VerticalLineProps(pos: (Int, Int),
                             length: Int,
                             lineCh: String,
                             style: BlessedStyle,
                             startCh: Option[String] = None,
                             endCh: Option[String] = None)

object VerticalLine extends FunctionComponent[VerticalLineProps] {
  
  protected def render(compProps: Props): ReactElement = {
    val props = compProps.wrapped
    val (left, top) = props.pos

    <.text(
      ^.rbWidth := 1,
      ^.rbHeight := props.length,
      ^.rbLeft := left,
      ^.rbTop := top,
      ^.rbStyle := props.style,
      ^.content := {
        val startCh = props.startCh.getOrElse("")
        val endCh = props.endCh.getOrElse("")

        startCh +
          props.lineCh * (props.length - startCh.length - endCh.length) +
          endCh
      }
    )()
  }
}
