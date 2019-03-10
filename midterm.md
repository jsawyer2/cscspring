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
  
  
  
  
  
  # i was having some difficulty finding the filepath for the text, it appears that I have found it, but it refueses to load it
  # An n-gram is a set of numbers/text/characters. We have been using n-grams in our work to find the "bad-words" and such in our texts.
  # These n-grams are useful in numerous ways that allow people to analyze certain elements of texts or sets of numbers. It is an
  # easy way to truncate information and make it easy to read. To find an n-gram you need to use certain mathematical equations to 
  # find exactly what probability or sequence you are looking for. The command .size is an excellent way to find the word count of a
  # file, which would classify as an n-gram.
  
