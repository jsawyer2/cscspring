import scala.io.Source
import edu.holycross.shot.cite

val myVector = Vector("a","b","c")


println("hi!")





val filepath:String = "/Users/sawye/Desktop/vm_Spring_2019/workspace2019/huckfinn2.txt"
val myLines:Vector[String = Source.fromFile(filepath).getLines.toVector

val wordVec: Vector[String] = {
  val bigString:String = myLines.mkString(" ")
  val noPunc:String = bigString.replaceAll("""[\\[\\],.?;":!)(]""","").
    replaceAll("[---]"," ").replaceAll(" +"," ")
  val tokenizedVector:Vector[String] = noPunc.split(" ").toVector.filter( _.size > 0)
  tokenizedVector
}
