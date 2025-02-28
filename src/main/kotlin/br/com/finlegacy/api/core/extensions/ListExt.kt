package br.com.finlegacy.api.core.extensions

import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.SortOrder


fun <T> List<T>.asSizedIterable(): SizedIterable<T> {
    return object : SizedIterable<T> {

        override fun iterator(): Iterator<T> = this@asSizedIterable.iterator()

        override fun copy(): SizedIterable<T> = this@asSizedIterable.toList().asSizedIterable()

        override fun count(): Long = this@asSizedIterable.size.toLong()

        override fun empty(): Boolean = this@asSizedIterable.isEmpty()

        override fun limit(n: Int, offset: Long): SizedIterable<T> {
            val subList = this@asSizedIterable.drop(offset.toInt()).take(n)
            return subList.asSizedIterable()
        }

        override fun orderBy(vararg order: Pair<Expression<*>, SortOrder>): SizedIterable<T> {
            // Aqui, você pode implementar a lógica de ordenação se necessário.
            // Para fins de teste, podemos apenas retornar a lista original.
            return this@asSizedIterable.asSizedIterable()
        }
    }
}
