import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import {Route, Routes} from "react-router-dom"
import Navbar from "./NavBar";
import Score from "./pages/Score";
import Home from "./pages/Home";
import Game from "./pages/Game";
import Rating from "./pages/Rating";
import Profile from "./pages/Profile";
import {useEffect, useState} from "react";

import {
    createUserWithEmailAndPassword, onAuthStateChanged,
    signInWithEmailAndPassword,
    signOut,
    updateProfile
} from 'firebase/auth'
import {auth, upload} from './firebase-config'
import Comment from "./pages/Comment";

function App() {
    const game = "pipes";

    const [user, setUser] = useState({});
    const [registerEmail, setRegisterEmail] = useState([]);
    const [registerPassword, setRegisterPassword] = useState([]);
    const [loginEmail, setLoginEmail] = useState([]);
    const [loginPassword, setLoginPassword] = useState([]);
    const [userName, setUserName] = useState([]);


    useEffect(() => {
        onAuthStateChanged(auth, (currentUser) => {
            setUser(currentUser);
        });
    }, [user]);

    const register = async () => {
        try {
            const currUser = await createUserWithEmailAndPassword(auth, registerEmail, registerPassword);
            await updateProfile(currUser.user, { displayName: userName }).then(() => {
                setUser(currUser);
            });
        } catch (error) {
            console.log(error.message);
        }
    };

    const login = async () => {
        try {
            const currUser = await signInWithEmailAndPassword(
                auth,
                loginEmail,
                loginPassword
            );
            setUser(currUser);
        } catch (error) {
            console.log(error.message);
        }
    };

    const logOut = async () => {
        await signOut(auth);
    };

    const updatePhoto = async (photo, currUser, setLoading) => {
        upload(photo, currUser, setLoading).then(() => {
                console.log("Photo uploaded")
                setUser(auth.currentUser);
            }
        );
    }

    return (
        <>
            <Navbar profile={user}/>
            <div className="container">
                <Routes>
                    <Route path="/" element={<Home register={register} login={login} logout={logOut}
                                                   setLoginEmail={setLoginEmail} setLoginPassword={setLoginPassword}
                                                   setRegisterEmail={setRegisterEmail}
                                                   setRegisterPassword={setRegisterPassword}
                                                   user={user}
                                                    setUserName={setUserName}/>}/>
                    {user?.displayName ? (
                        <Route path="/game" element={<Game player={user?.displayName} game={game}/>}/>
                     ) : (
                        <Route path="/game" element={<Home register={register} login={login} logout={logOut}
                        setLoginEmail={setLoginEmail} setLoginPassword={setLoginPassword}
                        setRegisterEmail={setRegisterEmail}
                        setRegisterPassword={setRegisterPassword}
                        user={user}
                        setUserName={setUserName}/>}/>
                        )}
                    <Route path="/rating" element={<Rating/>}/>
                    <Route path="/score" element={<Score/>}/>
                    <Route path="/comment" element={<Comment/>}/>
                    {user ? (
                        <Route path="/profile" element={<Profile logout={logOut} user={user} upload={updatePhoto}/>}/>
                    ) : (
                        <Route path="/profile" element={<Home register={register} login={login} logout={logOut}
                                                       setLoginEmail={setLoginEmail} setLoginPassword={setLoginPassword}
                                                       setRegisterEmail={setRegisterEmail}
                                                       setRegisterPassword={setRegisterPassword}
                                                       user={user}
                                                       setUserName={setUserName}/>}/>
                    )}
                </Routes>
            </div>
        </>
    )
}

export default App
