package com.kk.data

import com.kk.data.models.events.Answer

interface AnswerDataSource {
    fun addAnswer(answer: Answer)
    fun getAnswersByCode(code: String): List<Answer>
    fun removeAll(answers: List<Answer>)
}