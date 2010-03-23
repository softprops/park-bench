# Park Bench

An elderly man sitting on a park bench watching your code execute, then telling you about it over chess stategy pointers and life stories.

## Usage

Inline Benches

    scala> new park.Bench() { 
      def run = Thread.sleep(1000)
      override def subject = "sleeping beauty" 
    } watch(1, 1)
    
                   		cpu		    user		  system		real
    sleeping beauty		0.000634	0.000133	0.000087	1.000265


Benches as objects

    case class Book(name: String, pages: Int)
   
    class BookShelf { def read(book: Book) = Thread.sleep(1000 * book.pages) }
   
    object BookShelfBench extends BookShelf with park.Bench {
      def run = read(Book("a tale of 2 pages", 2))
      override def subject = "reading books from the shelf"
    }
   
    BookShelfBench.watch(1, 1)
                                		cpu		    user		  system		real
    reading books from the shelf		0.000672	0.000614	0.000083	2.000975

    
    // with 2 iterations of `run`
    BookShelfBench.watch(2, 1)
                                		cpu		    user		  system		real
    reading books from the shelf		0.000261	0.000182	0.000124	4.000479
    
    // with 2 observations of 2 iterations of `run`
    BookShelfBench.watch(2, 2)
                                		cpu		    user		  system		real
    reading books from the shelf		0.000228	0.000156	0.000101	4.000368
    reading books from the shelf		0.000208	0.000164	0.000083	4.000519
   
Or how about running this as a main appliction

    BookShelfBench.main(Array("1","1"))

## Install

  TODO
  
## TODO

* tests

2010 Doug Tangren (softprops)