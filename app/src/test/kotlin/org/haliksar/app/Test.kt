package org.haliksar.app

import org.haliksar.feature.bar.BarTagGroup
import org.haliksar.shared.foo.FooTagGroup
import org.haliksar.shared.foo.TextTagGroup

interface Node<T> {

    val tagGroup: T
}

object FooScreen : Node<FooTagGroup> {
    override val tagGroup: FooTagGroup = FooTagGroup

    val root = tagGroup.SCREEN

    val text = Text
}

object BarScreen : Node<BarTagGroup> {
    override val tagGroup: BarTagGroup = BarTagGroup

    val root = tagGroup.SCREEN
}

object Text : Node<TextTagGroup> {
    override val tagGroup: TextTagGroup = TextTagGroup

    val text = tagGroup.TEXT
}


class TestFoo {

    fun testFoo() {
        println(FooScreen.text)
        println(FooScreen)
    }
}

class TestBar {

    fun testFoo() {
        println(BarScreen)
    }
}