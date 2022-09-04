package com.estiam.arpenteurs.ui.authentification

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import com.estiam.arpenteurs.MainActivity
import com.estiam.arpenteurs.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton

/**
 * Fragment d'authenfication de l'utilisateur.
 * La classe utilise le package [com.google.android.gms] afin de s'authentifier avec un compte Google
 */
class LoginFragment : Fragment() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        // activity du parent
        mainActivity = activity as MainActivity

        // paramètrage des données utilisateur requis à l'application - Email et données de profil
        val authDataOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        // construit le client Google avec les paramètres associés
        mGoogleSignInClient = GoogleSignIn.getClient(mainActivity, authDataOptions);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // lance l'authentification Google au click du boutton sign_in_button
        view.findViewById<SignInButton>(R.id.sign_in_button).setOnClickListener {
            signInWithGoogle()
        };
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.show_logout).isVisible = false
    }

    /**
     * Lance l'activity Google auth
     */
    private fun signInWithGoogle() {
        startGoogleSignInActivity.launch(mGoogleSignInClient.signInIntent)
    }

    /**
     * Déconnecte l'utilisateur du compte Google actuellement actif
     */
    fun logoutFromGoogle() {
        mGoogleSignInClient.signOut()
    }

    /**
     * Gestionnaire de résultat pour le retour de l'activity Google auth
     */
    private val startGoogleSignInActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // si -1, l'utilisateur est connecté, et est donc redirigé vers la page home
        if(result.resultCode == -1) {
            mainActivity.redirectToMainFragmentIfIsConnected()
        } else {
            // affiche une popup de message d'erreur
            val appContext = context?.applicationContext
            Toast.makeText(appContext, "Un problème est survenu lors de la connection à votre compte Google", 4000).show()
        }
    }
}