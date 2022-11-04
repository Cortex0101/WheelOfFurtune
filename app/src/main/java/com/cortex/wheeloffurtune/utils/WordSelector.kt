package com.cortex.wheeloffurtune.utils

data class Word(val category: String, val word: String)

fun getRandomWord(words: Array<String>): Word {
    val randomWord = words.random()
    val wordAndCategory = randomWord.split("\\s".toRegex()).toTypedArray()
    return Word(wordAndCategory[0], wordAndCategory[1])
}

fun extendWordToSize(word: String, size: Int): String {
    val builder = StringBuilder()
    builder.append("||")
    builder.append(word)
    builder.append("||")
    val remaining = size - builder.length

    if (remaining < 0)
        throw Exception("Cant fit the word")

    for (i in 1..remaining)
        builder.append('|')
    return builder.toString()
}

fun replaceUnderscoresWithSpace(word: String): String {
    return word.replace('_', ' ')
}