import './Pages.css'
import {useEffect, useState} from "react";

export default function Profile(props) {
    const [photo, setPhoto] = useState(null);
    const [loading, setLoading] = useState(false);
    const [photoURL, setPhotoURL] = useState("https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png");

    useEffect(() => {
        if (props.user?.photoURL) {
            setPhotoURL(props.user.photoURL);
        }
    }, [props.user])

    function handleChange(e) {
        if (e.target.files[0]) {
            setPhoto(e.target.files[0])
        }
    }

    function handleClick() {
        props.upload(photo, props.user, setLoading);
    }

    return (
        <div id="signInDiv">
            {props.user && (
                <div>
                    {props.user.photoURL ? (
                        <div>
                            <div className="profile-picture-container">
                                <img className="profile-picture" src={props.user.photoURL} alt="user image"/>
                            </div>
                            <div className="upload-photo-button">
                                <input placeholder="Change picture" type="file" onChange={handleChange} accept="image/*"
                                       required id="images"/>
                                <button className="register-button" disabled={loading || !photo}
                                        onClick={handleClick}>Upload
                                </button>
                            </div>
                        </div>
                    ) : (
                        <div className="upload-photo-button">
                            <input placeholder="Change picture" type="file" onChange={handleChange} accept="image/*"
                                   required id="images"/>
                            <button className="register-button" disabled={loading || !photo}
                                    onClick={handleClick}>Upload
                            </button>
                        </div>
                    )}
                    <h3>User Logged in</h3>
                    <p>Name: {props.user.displayName}</p>
                    <p>Email Address: {props.user.email}</p>
                    <br/>
                    <br/>
                    <button className="register-button" onClick={props.logout}>Log out</button>
                </div>
            )}
        </div>
    )
}