package com.kk.data

import com.kk.data.models.events.Answer
import java.util.*

class AnswerDataSourceImp: AnswerDataSource {

    private val answers = Collections.synchronizedSet<Answer>(LinkedHashSet())
    override fun addAnswer(answer: Answer) {
        answers.add(answer)
    }

    override fun getAnswersByCode(code: String): List<Answer> {
        return answers.filter { it.gameCode == code }
    }

    override fun removeAll(answers: List<Answer>) {
        this.answers.removeAll(answers.toSet())
    }

}