import scala.io.Source

val myVector = Vector("a","b","c")

println("hi!")

val filepath:String = "/Desktop/vm_spring_2019/workspace2019/texts/huckfinn2.txt"
val myLines:Vector[String] = Source.fromFile(filepath).getLines.toVector

val wordVec: Vector[String] = {
  val bigString:String = myLines.mkString(" ")
  val noPunc:String = bigString.replaceAll("""[\\[\\],.?;":!)(]""","").
    replaceAll("[---]"," ").replaceAll(" +"," ")
  val tokenizedVector:Vector[String] = noPunc.split(" ").toVector.filter( _.size > 0)
  tokenizedVector
}

  val wordMap:Map[String,Vector[String]] = wordVec.groupBy(w => w)
  val quantMap:Map[String,Int] = wordMap.map(m => (m._1, m._2.size))
  val mapVec:Vector[(String,Int)] = quantMap.toVector
  val wordHisto = mapVec.sortBy(_._2).reverse
  val uniqueWords:Vector[String] = wordHisto.map(_._1)

  println(s"\n\n--------\nThere are ${wordHisto.size} unique words. \n------\n")
