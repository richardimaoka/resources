package example


trait A {
  val a: Int
}

class AA extends A{
  lazy val a = 10
}

//trait B {
//  lazy val b: Int // [compile error]: lazy values may not be abstract
//}
//
//class BB extends B {
//  val b = 10
//}

//trait C {
//  val c = 10
//}
//
//class CC extends {
//  lazy val c: Int = 10
//}
//
//trait D {
//  lazy val d = 10
//}
//
//class DD extends D {
//  val d = 0
//}
