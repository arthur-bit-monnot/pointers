package pointers

import minitest._
import pointers.impl.SimpleMemImpl

object Tests extends SimpleTestSuite {

  test("scalars") {
    val m = new SimpleMemImpl

    val a = m.alloc[Int]
    assert(m.readInt(a) == 0)
    m.writeInt(a, 1)
    assert(m.readInt(a) == 1)

    val d = m.alloc[Double]
    val b = m.alloc[Int]
    assert(m.readDouble(d) == 0.0)
    m.writeDouble(d, 5.0)
    assert(m.readDouble(d) == 5.0)

    // check that we did not modify a / b
    assert(m.readInt(a) == 1)
    assert(m.readInt(b) == 0)
  }

  test("point") {

    val m = new SimpleMemImpl

    val pt = m.alloc(Point)
    val x  = pt(Point.x)
    val y  = pt(Point.y)
    val z  = pt(Point.z)

    m.writeInt(x, 1)
    m.writeInt(y, 2)
    m.writeDouble(z, 5)

    assert(m.readInt(x) == 1)
    assert(m.readInt(y) == 2)
    assert(m.readDouble(z) == 5.0)

  }

  test("vecs") {
    val m = new SimpleMemImpl

    val vec = m.allocVec[Int](100)

    assert(vec.length == 100)
    assert(vec.tail.length == 99)

    var curr = vec.first
    var i = 0
    val last = vec.last
    while(curr <= last) {
      m.writeInt(curr, i)
      i += 1
      curr = curr.unsafeNext
    }

    for(i <- 0 until 100) {
      assert(m.readInt(vec(i)) == i)
    }
  }

}
