// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth, GoogleAuthProvider, signInWithPopup, updateProfile} from 'firebase/auth'
import { getDownloadURL, getStorage, ref, uploadBytes } from "firebase/storage";
//import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
    apiKey: "AIzaSyDFRikKuU9q4SON_NYQbXNSOp5K9Y5T_zU",
    authDomain: "plumbergame-bbb4c.firebaseapp.com",
    projectId: "plumbergame",
    storageBucket: "plumbergame.appspot.com",
    messagingSenderId: "490401947366",
    appId: "1:490401947366:web:86c1e5eb9784f03103e527",
    measurementId: "G-264NPBVFFK"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
// const analytics = getAnalytics(app);
const storage = getStorage();
export const auth = getAuth(app);

const provider = new GoogleAuthProvider();

export const signInWithGoogle = () => {
    signInWithPopup(auth, provider)
        .then((result) => {
            const name = result.user.displayName;
            const email = result.user.email;
            const profilePic = result.user.photoURL;

            localStorage.setItem("name", name);
            localStorage.setItem("email", email);
            localStorage.setItem("profilePic", profilePic);
            console.log(result.user)
        })
        .catch((error) => {
            console.log(error);
        });
};

export async function upload(file, currentUser, setLoading) {
    const fileRef = ref(storage, currentUser.uid + '.png');

    setLoading(true);

    const snapshot = await uploadBytes(fileRef, file);
    const photoURL = await getDownloadURL(fileRef);

    await updateProfile(currentUser, {photoURL});

    setLoading(false);
    alert("Uploaded file!");
}