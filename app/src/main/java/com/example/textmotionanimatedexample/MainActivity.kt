package com.example.textmotionanimatedexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.textmotionanimatedexample.ui.theme.TextMotionAnimatedExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TextMotionAnimatedExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AnimatedTextComponent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun AnimatedTextComponent(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("Hello!") }
    var isAnimating by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isAnimating) 1.6f else 0.5f,
        animationSpec = tween(durationMillis = 500)
    )

    val scale by animateFloatAsState(
        targetValue = if (isAnimating) 2.5f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    AnimatedText(
        text = text,
        scale = scale,
        alpha = alpha,
        onClick = {
            isAnimating = !isAnimating
            text = if (text == "Hello!") "World!" else "Hello!"
        },
        modifier = modifier
    )
}

@Composable
fun AnimatedText(
    text: String,
    scale: Float,
    alpha: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = text,
                style = LocalTextStyle.current.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        alpha = alpha
                    )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onClick) {
                Text("Switch")
            }
        }
    }
}

