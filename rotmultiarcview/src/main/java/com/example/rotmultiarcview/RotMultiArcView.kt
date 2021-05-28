package com.example.rotmultiarcview

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Canvas
import android.graphics.Color
import android.app.Activity
import android.content.Context

val colors : Array<Int> = arrayOf(
    "#f44336",
    "#673AB7",
    "#00C853",
    "#304FFE",
    "#BF360C"
).map {
    Color.parseColor(it)
}.toTypedArray()
val arcs : Int = 3
val parts : Int = 2
val scGap : Float = 0.02f / (parts * arcs)
val sizeFactor : Float = 2.9f
val strokeFactor : Float = 90f
val delay : Long = 20
val backColor : Int = Color.parseColor("#BDBDBD")
val start : Float = 30f
val deg : Float = 300f

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawRotMultiArc(scale : Float, w : Float, h : Float, paint : Paint) {
    val size : Float = Math.min(w, h) / sizeFactor
    val sf : Float = scale.sinify()
    var k : Float = 1f
    save()
    translate(w / 2, h / 2)
    for (j in 0..(arcs - 1)) {
        val r : Float = size * k
        val sfj : Float = sf.divideScale(j, arcs)
        val sfj1 :Float = sfj.divideScale(0, parts)
        val sfj2 : Float = sfj.divideScale(1, parts)
        save()
        rotate(90f * sfj2)
        drawArc(RectF(-r, -r, r, r), start, deg * sfj1, false, paint)
        restore()
        k *= 0.9f
    }
    restore()
}

fun Canvas.drawRMANode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = colors[i]
    paint.strokeCap = Paint.Cap.ROUND
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    drawRMANode(i, scale, paint)
}