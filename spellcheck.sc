import scala.io.Source
import edu.holycross.shot.cite

val filepath:String = "texts/huckfinn2.txt"
val myLines:Vector[String] = Source.fromFile(filepath).getLines.toVector

val wordVec: Vector[String] = {
  val bigString:String = myLines.mkString(" ")
  val noPunc:String = bigString.replaceAll("""[\\[\\],.?;":!)(]""","").
    replaceAll("[---]"," ").replaceAll(" +"," ")
  val tokenizedVector:Vector[String] = noPunc.split(" ").toVector.filter( _.size > 0)
  tokenizedVector
}

  val dictpath:String = "texts/huckfinn2.txt"
  val dictEntries:Vector[String] = Source.fromFile(dictpath).getLines.toVector.
    filter( _.size > 0 )

  val badWords:Vector[String] = uniqueWords.filter( w => {(dictEntries.contains(w.toLowerCase) == false)(dictEntries.contains(w) == false)S})

for (w <- badWords) {
  println(w)
}
