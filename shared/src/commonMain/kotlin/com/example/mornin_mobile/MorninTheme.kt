package com.example.mornin_mobile


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

// ── Warm Sunrise palette ──────────────────────────────────────
object MorninWarm {
    val Bg          = Color(0xFFFBF0DC)
    val Surface     = Color(0xFFFFF8EA)
    val SurfaceAlt  = Color(0xFFFFE7CB)
    val Text        = Color(0xFF2A1A0E)
    val TextMuted   = Color(0xFF7A5F44)
    val Rule        = Color(0xFFE9D6B6)
    val Accent      = Color(0xFFC25A2E)   // terracotta
}

// ── Fonts (drop the .ttf files in res/font) ───────────────────
// Newsreader for headlines, Geist for body, Geist Mono for labels.
//val Newsreader = FontFamily(
//    Font(R.font.newsreader_regular, FontWeight.Normal),
//    Font(R.font.newsreader_medium,  FontWeight.Medium),
//)
//val Geist = FontFamily(
//    Font(R.font.geist_regular, FontWeight.Normal),
//    Font(R.font.geist_medium,  FontWeight.Medium),
//)
//val GeistMono = FontFamily(
//    Font(R.font.geistmono_regular, FontWeight.Normal),
//    Font(R.font.geistmono_medium,  FontWeight.Medium),
//)