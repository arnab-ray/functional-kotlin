package ch3

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec

fun depth(tree: Tree<Int>): Int =
    when (tree) {
        is Leaf -> 0
        is Branch -> 1 + maxOf(depth(tree.left), depth(tree.right))
    }

class Exercise26 : WordSpec({
    "tree depth" should {
        "determine the maximum depth from the root to any leaf" {
            val tree = Branch(
                Branch(Leaf(1), Leaf(2)),
                Branch(
                    Leaf(3),
                    Branch(
                        Branch(
                            Leaf(4),
                            Leaf(5)
                        ),
                        Branch(
                            Leaf(6),
                            Branch(
                                Leaf(7),
                                Leaf(8)
                            )
                        )
                    )
                )
            )
            depth(tree) shouldBe 5
        }
    }
})