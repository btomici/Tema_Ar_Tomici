package ro.ac.upt.christmasarsample

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode


class AugmentedActivity : AppCompatActivity() {

    private lateinit var arFragment: ArFragment

    private var renderable: Renderable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_augmented)

        initRenderableModel()

        arFragment = supportFragmentManager.findFragmentById(R.id.scf_central) as ArFragment

        fun onTapListener(r: HitResult) {
            if (renderable != null) {
                addRenderableToScene(r.createAnchor(), renderable!!)
            }
        }
        arFragment.setOnTapArPlaneListener(
            fun (r: HitResult, p: Plane, mE: MotionEvent) {
                onTapListener(r);
            }
        )
    }

    private fun initRenderableModel() {
        val modelUri = Uri.parse("model.sfb")
        renderable = ModelRenderable.builder().build().get();
    }

    private fun addRenderableToScene(anchor: Anchor, renderable: Renderable) {
        val transformableNode = TransformableNode(arFragment.transformationSystem);
        val anchorNode = anchor.let {
            AnchorNode(it)
        }
        val arSceneView = arFragment.arSceneView;
        anchorNode.setParent(arSceneView.scene);
        transformableNode.setParent(anchorNode);
        transformableNode.renderable = renderable;
    }

    companion object {
        private val TAG = AugmentedActivity::class.java.simpleName
    }

}