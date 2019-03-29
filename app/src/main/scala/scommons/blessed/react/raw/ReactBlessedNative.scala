package scommons.blessed.react.raw

import io.github.shogowada.scalajs.reactjs.elements.ReactElement
import scommons.blessed.raw.BlessedScreen

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("react-blessed", JSImport.Default)
object ReactBlessedNative extends js.Object {

  def render(element: ReactElement, screen: BlessedScreen): Unit = js.native
}