package scommons.react.blessed.raw

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("blessed", JSImport.Default)
object Blessed extends js.Object {

  def screen(config: BlessedScreenConfig): BlessedScreen = js.native
}

@js.native
trait BlessedScreen extends js.Object {
  
  def focusOffset(offset: Int): Unit = js.native
  def key(keys: js.Array[String], onKey: js.Function2[js.Object, KeyboardKey, Unit]): Unit = js.native
  def destroy(): Unit = js.native
}

@js.native
trait BlessedElement extends js.Object {
  
  val width: Int = js.native  //Calculated width
  val height: Int = js.native //Calculated height
  val left: Int = js.native   //Calculated relative left offset
  val top: Int = js.native    //Calculated relative top offset
  val aleft: Int = js.native  //Calculated absolute left offset
  val atop: Int = js.native   //Calculated absolute top offset
  
  val screen: BlessedScreen = js.native
}

@js.native
trait MouseData extends js.Object {

  val button: String = js.native
  val x: Int = js.native
  val y: Int = js.native
  val shift: Boolean = js.native
  val ctrl: Boolean = js.native
  val meta: Boolean = js.native
}

@js.native
trait KeyboardKey extends js.Object {
  
  val name: String = js.native
  val full: String = js.native
  val shift: Boolean = js.native
  val ctrl: Boolean = js.native
  val meta: Boolean = js.native
}
