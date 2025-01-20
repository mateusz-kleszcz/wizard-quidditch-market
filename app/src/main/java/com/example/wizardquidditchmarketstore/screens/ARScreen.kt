package com.example.wizardquidditchmarketstore.screens

import android.view.MotionEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wizardquidditchmarketstore.models.offers.FirebaseViewModel
import com.example.wizardquidditchmarketstore.navigation.Screens
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.TrackingFailureReason
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.isValid
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.node.Node
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener
import io.github.sceneview.rememberView

@Composable
fun ARScreen(
    navController: NavController,
    firebaseViewModel: FirebaseViewModel,
    offerId: String,
    userId: String
    )
{
    var isSold by remember { mutableStateOf(false) }

    val offerDetails = firebaseViewModel.offerDetails ?: return

    var button by remember { mutableStateOf(false) }

    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine = engine)
    val materialLoader = rememberMaterialLoader(engine = engine)
    val cameraNode = rememberARCameraNode(engine = engine)
    val childNodes = rememberNodes()
    val view = rememberView(engine = engine)
    val collisionSystem = rememberCollisionSystem(view = view)
    val modelInstance = remember{
        mutableListOf<ModelInstance>()
    }

    val trackingFailureReason = remember{
        mutableStateOf<TrackingFailureReason?>(null)
    }
    val frame = remember {
        mutableStateOf<Frame?>(null)
    }

    ARScene(
        modifier = Modifier.fillMaxSize(),
        childNodes = childNodes,
        engine = engine,
        view = view,
        modelLoader = modelLoader,
        collisionSystem = collisionSystem,
        cameraNode =  cameraNode,
        materialLoader = materialLoader,
        onTrackingFailureChanged = {
            trackingFailureReason.value = it
        },
        onSessionUpdated = { _, updatedFrame ->
            frame.value = updatedFrame
        },
        sessionConfiguration = { session, config ->
            config.depthMode = when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                true -> Config.DepthMode.AUTOMATIC
                else -> Config.DepthMode.DISABLED
            }
            config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
        },
        onGestureListener = rememberOnGestureListener(
            onSingleTapConfirmed = { e: MotionEvent, node: Node? ->
                if (node == null && childNodes.isEmpty()){
                    val hitTestResult = frame.value?.hitTest(e.x,e.y)
                    hitTestResult?.firstOrNull{
                        it.isValid(
                            depthPoint = false,
                            point = false
                        )
                    }?.createAnchorOrNull()?.let{
                        val nodeModel = createAnchorNode(
                            engine = engine,
                            modelLoader = modelLoader,
                            materialLoader = materialLoader,
                            modelInstance = modelInstance,
                            anchor = it,
                            model = "3dmodels/owl.glb"
                        )
                        childNodes += nodeModel
                    }
                    button = true

                }
            }
        )
    )
    if (isSold){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("SOLD")
            Button(
                onClick = {navController.navigate(
                    Screens.OffersList.route
                )}
            ) { Text("Exit") }
        }

    }else{
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                modifier = Modifier,
                onClick = {
                    navController.navigate(
                        Screens.OfferDetails.route
                            .replace(
                                oldValue = "{id}",
                                newValue = offerId
                            )
                    )
                },
            ) {
                Text("Back")
            }
            if(button){
                Button(
                    modifier = Modifier,
                    onClick = {
                        childNodes.clear()
                        button = false
                    },
                ) {
                    Text("Clear")
                }
                Button(
                    modifier = Modifier,
                    onClick = {
                        if(offerDetails.sold==firebaseViewModel.get_auth().currentUser?.uid.toString()){
                            isSold = true
                            firebaseViewModel.removeOffer(offerId)
                        }else{
                            firebaseViewModel.setBuyer(offerId,userId){
                                navController.navigate(
                                    Screens.Profile.route
                                )
                            }
                        }

                    },
                ) {
                    Text("Check")
                }
            }
        }
    }


}

fun createAnchorNode(
    engine: Engine,
    modelLoader: ModelLoader,
    materialLoader: MaterialLoader,
    modelInstance: MutableList<ModelInstance>,
    anchor: Anchor,
    model:String
): AnchorNode {
    val anchorNode = AnchorNode(engine = engine, anchor = anchor)
    val modelNode = ModelNode(
        modelInstance = modelInstance.apply {
            if (isEmpty()){
                this += modelLoader.createInstancedModel(model,10)
            }
        }.removeAt(modelInstance.apply {
            if (isEmpty()){
                this += modelLoader.createInstancedModel(model,10)
            }
        }.lastIndex),
        scaleToUnits = 0.2f
    ).apply {
        isEditable = true
    }
    val boundingBox = CubeNode(
        engine = engine,
        size = modelNode.extents,
        center = modelNode.center,
        materialInstance = materialLoader.createColorInstance(Color.White)
    ).apply {
        isVisible = false
    }
    modelNode.addChildNode(boundingBox)
    anchorNode.addChildNode(modelNode)
    listOf(modelNode, anchorNode).forEach {
        it.onEditingChanged = { editingTransforms ->
            boundingBox.isVisible = editingTransforms.isNotEmpty()
        }
    }
    return anchorNode
}