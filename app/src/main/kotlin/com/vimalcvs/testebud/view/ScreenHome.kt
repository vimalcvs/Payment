package com.vimalcvs.testebud.view

import android.widget.Toast
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vimalcvs.testebud.R
import com.vimalcvs.testebud.theme.TesteBudTheme
import com.vimalcvs.testebud.view.FlipAnimationType.HORIZONTAL_ANTI_CLOCKWISE
import com.vimalcvs.testebud.view.FlipAnimationType.HORIZONTAL_CLOCKWISE
import com.vimalcvs.testebud.view.FlipAnimationType.VERTICAL_ANTI_CLOCKWISE
import com.vimalcvs.testebud.view.FlipAnimationType.VERTICAL_CLOCKWISE
import com.vimalcvs.testebud.view.FlippableState.BACK
import com.vimalcvs.testebud.view.FlippableState.FRONT
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

enum class FlippableState {
    INITIALIZED,
    FRONT,
    BACK
}

enum class FlipAnimationType {

    HORIZONTAL_CLOCKWISE,


    HORIZONTAL_ANTI_CLOCKWISE,


    VERTICAL_CLOCKWISE,

    VERTICAL_ANTI_CLOCKWISE
}

@Composable
fun ScreenHome(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp))
                .background(Color(0xFFF7F7F7))
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF7F7F7))
            ) {
                item {
                    CardFlip()
                }
                item {
                    ActionGrid(navController = navController)
                }
            }
        }
    }
}


@Composable
fun CardFlip() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            val duration: Int by remember { mutableIntStateOf(400) }
            val flipOnTouchEnabled: Boolean by remember { mutableStateOf(true) }
            val flipEnabled: Boolean by remember { mutableStateOf(true) }
            val autoFlipEnabled: Boolean by remember { mutableStateOf(false) }
            val selectedAnimType: FlipAnimationType by remember {
                mutableStateOf(
                    VERTICAL_ANTI_CLOCKWISE
                )
            }

            val flipController = remember(key1 = "1") {
                FlippableController()
            }
            Flippable(
                frontSide = {
                    CardSectionFront()
                },
                backSide = {
                    CardSectionBack()
                },
                flipController = flipController,
                flipDurationMs = duration,
                flipOnTouch = flipOnTouchEnabled,
                flipEnabled = flipEnabled,
                autoFlip = autoFlipEnabled,
                autoFlipDurationMs = 2000,
                flipAnimationType = selectedAnimType
            )
        }
    }
}

class FlippableController {

    private val _flipRequests = MutableSharedFlow<FlippableState>(extraBufferCapacity = 1)
    internal val flipRequests = _flipRequests.asSharedFlow()

    private var _currentSide: FlippableState = FRONT
    private var _flipEnabled: Boolean = true

    fun flipToFront() {
        flip(FRONT)
    }

    fun flipToBack() {
        flip(BACK)
    }


    private fun flip(flippableState: FlippableState) {
        if (_flipEnabled.not())
            return

        _currentSide = flippableState
        _flipRequests.tryEmit(flippableState)
    }


    internal fun setConfig(
        flipEnabled: Boolean = true,
    ) {
        _flipEnabled = flipEnabled
    }
}


@Composable
fun Flippable(
    frontSide: @Composable () -> Unit,
    backSide: @Composable () -> Unit,
    flipController: FlippableController,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    flipDurationMs: Int = 400,
    flipOnTouch: Boolean = true,
    flipEnabled: Boolean = true,
    autoFlip: Boolean = false,
    autoFlipDurationMs: Int = 1000,
    cameraDistance: Float = 30.0F,
    flipAnimationType: FlipAnimationType = HORIZONTAL_CLOCKWISE,
    onFlippedListener: (currentSide: FlippableState) -> Unit = { _ -> }
) {
    var prevViewState by remember { mutableStateOf(FlippableState.INITIALIZED) }
    var flippableState by remember { mutableStateOf(FlippableState.INITIALIZED) }
    val transition: Transition<FlippableState> = updateTransition(
        targetState = flippableState,
        label = "Flip Transition",
    )
    flipController.setConfig(
        flipEnabled = flipEnabled
    )

    LaunchedEffect(key1 = flipController, block = {
        flipController.flipRequests
            .onEach {
                prevViewState = flippableState
                flippableState = it
            }
            .launchIn(this)
    })

    val flipCall: () -> Unit = {
        if (transition.isRunning.not() && flipEnabled) {
            prevViewState = flippableState
            if (flippableState == FRONT)
                flipController.flipToBack()
            else flipController.flipToFront()
        }
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = transition.currentState, block = {
        if (transition.currentState == FlippableState.INITIALIZED) {
            prevViewState = FlippableState.INITIALIZED
            flippableState = FRONT
            return@LaunchedEffect
        }

        if (prevViewState != FlippableState.INITIALIZED && transition.currentState == flippableState) {
            onFlippedListener.invoke(flippableState)

            if (autoFlip && flippableState != FRONT) {
                scope.launch {
                    delay(autoFlipDurationMs.toLong())
                    flipCall()
                }
            }
        }
    })

    val frontRotation: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FRONT isTransitioningTo BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        90f at flipDurationMs / 2
                        90f at flipDurationMs
                    }
                }

                BACK isTransitioningTo FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        90f at 0
                        90f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Front Rotation"
    ) { state ->
        when (state) {
            FlippableState.INITIALIZED, FRONT -> 0f
            BACK -> 180f
        }
    }

    val backRotation: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FRONT isTransitioningTo BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        90f at 0
                        90f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                BACK isTransitioningTo FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        90f at flipDurationMs / 2
                        90f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Back Rotation"
    ) { state ->
        when (state) {
            FlippableState.INITIALIZED, FRONT -> 180f
            BACK -> 0f
        }
    }

    val frontOpacity: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FRONT isTransitioningTo BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        1f at 0
                        1f at (flipDurationMs / 2) - 1
                        0f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                BACK isTransitioningTo FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        0f at (flipDurationMs / 2) - 1
                        1f at flipDurationMs / 2
                        1f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Front Opacity"
    ) { state ->
        when (state) {
            FlippableState.INITIALIZED, FRONT -> 1f
            BACK -> 0f
        }
    }

    val backOpacity: Float by transition.animateFloat(
        transitionSpec = {
            when {
                FRONT isTransitioningTo BACK -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        0f at 0
                        0f at (flipDurationMs / 2) - 1
                        1f at flipDurationMs / 2
                        1f at flipDurationMs
                    }
                }

                BACK isTransitioningTo FRONT -> {
                    keyframes {
                        durationMillis = flipDurationMs
                        1f at 0
                        1f at (flipDurationMs / 2) - 1
                        0f at flipDurationMs / 2
                        0f at flipDurationMs
                    }
                }

                else -> snap()
            }
        },
        label = "Back Opacity"
    ) { state ->
        when (state) {
            FlippableState.INITIALIZED, FRONT -> 0f
            BACK -> 1f
        }
    }

    Box(
        modifier = modifier
            .background(Color(0xFFF7F7F7))
            .clickable(
                enabled = flipOnTouch,
                onClick = {
                    flipCall()
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = contentAlignment


    ) {

        Box(modifier = Modifier
            .graphicsLayer {
                this.cameraDistance = cameraDistance
                when (flipAnimationType) {
                    HORIZONTAL_CLOCKWISE -> rotationY = backRotation
                    HORIZONTAL_ANTI_CLOCKWISE -> rotationY = -backRotation
                    VERTICAL_CLOCKWISE -> rotationX = backRotation
                    VERTICAL_ANTI_CLOCKWISE -> rotationX = -backRotation
                }
            }
            .alpha(backOpacity)
            .background(Color(0xFFF7F7F7))
            .zIndex(1F - backOpacity)
        ) {
            backSide()
        }

        Box(modifier = Modifier
            .graphicsLayer {
                this.cameraDistance = cameraDistance
                when (flipAnimationType) {
                    HORIZONTAL_CLOCKWISE -> rotationY = frontRotation
                    HORIZONTAL_ANTI_CLOCKWISE -> rotationY = -frontRotation
                    VERTICAL_CLOCKWISE -> rotationX = frontRotation
                    VERTICAL_ANTI_CLOCKWISE -> rotationX = -frontRotation
                }
            }
            .alpha(frontOpacity)
            .background(Color(0xFFF7F7F7))
            .zIndex(1F - frontRotation)
        ) {
            frontSide()
        }
    }
}

@Composable
fun CardSectionFront() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 18.dp)
            .height(230.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(216.dp)
                .padding(start = 22.dp, end = 22.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFFF4267))

        ) {

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(208.dp)
                .padding(start = 12.dp, end = 12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF5655B9))
        ) {

        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF1E1671))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_card_font),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "John Smith",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Amazon Platinum", fontSize = 18.sp, color = Color.White
                        )
                        Text(
                            text = "4756 **** **** 9018", fontSize = 18.sp, color = Color.White
                        )
                        Text(
                            text = "$3,469.52",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CardSectionBack() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 18.dp)
            .height(230.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(216.dp)
                .padding(start = 22.dp, end = 22.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF1E1671))

        ) {

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(208.dp)
                .padding(start = 12.dp, end = 12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF5655B9))
        ) {

        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF1E1671))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_card_back),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "John Smith",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Platinum", fontSize = 18.sp, color = Color.White
                        )
                        Text(
                            text = "7756 **** **** 9018", fontSize = 18.sp, color = Color.White
                        )
                        Text(
                            text = "$1,069.52",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ActionGrid(navController: NavController = rememberNavController()) {
    val actions = listOf(
        "Account and Card" to R.drawable.ic_account,
        "Transfer" to R.drawable.ic_transfer,
        "Withdraw" to R.drawable.ic_withdraw,
        "Mobile prepaid" to R.drawable.ic_mobile,
        "Pay the bill" to R.drawable.ic_receipt,
        "Save online" to R.drawable.ic_save_online,
        "Credit card" to R.drawable.ic_credit_card,
        "Transaction report" to R.drawable.ic_transaction,
        "Beneficiary" to R.drawable.ic_beneficiary,
        "Test" to R.drawable.ic_transaction,
        "Test" to R.drawable.ic_transaction,
        "Test" to R.drawable.ic_transaction,
        "Test" to R.drawable.ic_transaction,
        "Test" to R.drawable.ic_transaction,
        "Test" to R.drawable.ic_transaction,
        "Test" to R.drawable.ic_transaction,
    )

    val numColumns = 3
    val numRows = (actions.size + numColumns - 1) / numColumns

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        val itemSize = maxWidth / numColumns
        Column {
            for (rowIndex in 0 until numRows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (columnIndex in 0 until numColumns) {
                        val actionIndex = rowIndex * numColumns + columnIndex
                        if (actionIndex < actions.size) {
                            ActionItem(
                                title = actions[actionIndex].first,
                                iconRes = actions[actionIndex].second,
                                navController = navController,
                                modifier = Modifier.size(itemSize)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActionItem(
    title: String,
    iconRes: Int,
    navController: NavController = rememberNavController(),
    modifier: Modifier
) {
    val context = LocalContext.current
    Card(
        onClick = {
            when (title) {
                "Transfer" -> navController.navigate("confirm")
                "Pay the bill" -> navController.navigate("payment")
                else -> {
                    Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
                }
            }
        }, colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF),
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 0.1.dp
        ), modifier = modifier
            .padding(3.dp)
            .clip(RoundedCornerShape(12.dp))
            .aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0x45E8EAF6))
                    .padding(12.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color(0xFF757575),
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center,
                style = TextStyle(lineHeight = 18.sp)
            )
        }
    }
}


@Preview
@Composable
fun PreviewHomeScreens() {
    TesteBudTheme {
        CardSectionBack()
    }
}

