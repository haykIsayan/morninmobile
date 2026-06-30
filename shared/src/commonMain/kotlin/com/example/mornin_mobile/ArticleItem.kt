package com.example.mornin_mobile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mornin_mobile.domain.entity.ArticleEntity
import com.example.mornin_mobile.util.formatPublishedDate

@Composable
fun ArticleItem(index: Int, article: ArticleEntity) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp)
        ) {
            // ── kicker: "02 · TECH" ──────────────────────────
            Text(
//                text = "%02d · %s".format(article.index, article.topic.uppercase()),
                text = "0${index + 1} · ${article.topic.uppercase()} · ${formatPublishedDate(article.publishedDate).uppercase()}",
//                fontFamily = GeistMono,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                letterSpacing = 1.4.sp,   // ~0.14em
                color = MorninWarm.Accent,
            )

            Spacer(Modifier.height(6.dp))

            // ── headline ─────────────────────────────────────
            Text(
                text = article.title,
//                fontFamily = Newsreader,
                fontWeight = FontWeight.Medium,
                fontSize = 19.5.sp,
                lineHeight = 23.sp,        // ~1.18
                letterSpacing = (-0.2).sp,
                color = MorninWarm.Text,
            )

            Spacer(Modifier.height(6.dp))

            // ── dek ──────────────────────────────────────────
            Text(
                text = article.summary,
//                fontFamily = Geist,
                fontSize = 13.5.sp,
                lineHeight = 19.5.sp,      // ~1.45
                color = MorninWarm.TextMuted,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(Modifier.height(8.dp))

            // ── meta row: read time + actions ────────────────
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = /*article.readTime.uppercase()*/"3 MIN",
//                    fontFamily = GeistMono,
                    fontSize = 10.sp,
                    letterSpacing = 0.8.sp,
                    color = MorninWarm.TextMuted,
                )
                Spacer(Modifier.width(12.dp))
//                Icon(
//                    imageVector = Icons.Outlined.BookmarkBorder,
//                    contentDescription = "Save",
//                    tint = MorninWarm.TextMuted,
//                    modifier = Modifier.size(16.dp),
//                )
//                Spacer(Modifier.width(12.dp))
//                Icon(
//                    imageVector = Icons.Outlined.Share,
//                    contentDescription = "Share",
//                    tint = MorninWarm.TextMuted,
//                    modifier = Modifier.size(16.dp),
//                )
            }
        }

        // ── separator ────────────────────────────────────────
        HorizontalDivider(thickness = 1.dp, color = MorninWarm.Rule)
    }

}

@Preview
@Composable
fun ArticleItemPreview() {
    ArticleItem(
        index = 1,
        article = ArticleEntity(
            title = "Stanford Quantum Computing Breakthrough Uses Twisted Light to Work Without Extreme Cooling",
            topic = "Technology",
            summary = "Stanford researchers have developed a new room-temperature quantum device that uses twisted light to entangle photons and electrons, overcoming one of the biggest hurdles in quantum technology. The breakthrough could pave the way for smaller, cheaper quantum systems with applications ranging from secure communications to future AI and computing.",
            source = "ScienceDaily",
            url = "https://example.com/test-article",
            publishedDate = "2026-05-30"
        )
    )
}