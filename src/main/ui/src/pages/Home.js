import './Pages.css';
import './Pages.scss'
import {signInWithGoogle} from "../firebase-config";

export default function Home(props) {
    return (
        <div>
            {props.user ? (
                <div>
                    <h3>You are logged in!</h3>
                </div>
            ) : (
                <div className="login-register-field">
                    <div className="register-field" id="register-field" style={{display: 'none'}}>
                        <div className="login-register-input">
                        <h3>Register</h3>
                        </div>
                        <div className="login-register-input">
                        <input
                            placeholder="User Name..."
                            onChange={(event) => {
                                props.setUserName(event.target.value)
                            }}
                            className="input-form"
                        />
                        </div>
                        <div className="login-register-input">
                        <input
                            placeholder="Email..."
                            onChange={(event) => {
                                props.setRegisterEmail(event.target.value)
                            }}
                            className="input-form"
                        />
                        </div>
                        <div className="login-register-input">
                        <input
                            type="password"
                            placeholder="Password..."
                            onChange={(event) => {
                                props.setRegisterPassword(event.target.value)
                            }}
                            className="input-form"
                        />
                        </div>
                        <div className="login-register-input">
                        <button className="register-button" onClick={props.register}>Register</button>
                        </div>
                        <div className="login-register-input">
                        <h6>Have an account? <a className="redirect-button" onClick={() => {
                            document.getElementById("register-field").style.display = 'none'
                            document.getElementById("login-field").style.display = ''
                        }}
                        >Sign in</a></h6>
                        </div>
                        <div className="line"></div>
                        <div className="login-register-input">
                            <button className="login-with-google-btn" onClick={signInWithGoogle}>Sign up with google
                            </button>
                        </div>
                    </div>
                    <div className="login-field" id="login-field">
                        <div className="login-register-input">
                        <h3>Login</h3>
                        </div>
                        <div className="login-register-input">
                        <input
                            placeholder="Email..."
                            onChange={(event) => {
                                props.setLoginEmail(event.target.value)
                            }}
                            className="input-form"
                        />
                        </div>
                        <div className="login-register-input">
                        <input
                            type="password"
                            placeholder="Password..."
                            onChange={(event) => {
                                props.setLoginPassword(event.target.value)
                            }}
                            className="input-form"
                        />
                        </div>
                        <div className="login-register-input">
                        <button className="login-button" onClick={props.login}>Login</button>
                        </div>
                        <div className="login-register-input">
                        <h6>Dont have an account? <a className="redirect-button" onClick={() => {
                            document.getElementById("register-field").style.display = ''
                            document.getElementById("login-field").style.display = 'none'
                        }}>
                            Sign up </a></h6>
                        </div>
                        <div className="line"></div>
                        <div className="login-register-input">
                            <button className="login-with-google-btn" onClick={signInWithGoogle}>Sign in with google
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {/*    {props.profile?.name ? (*/}
            {/*            <div>*/}
            {/*                <h1>You are logged!</h1>*/}
            {/*            </div>*/}
            {/*    ) : (*/}
            {/*        <div>*/}
            {/*            <button className="login-button" onClick={() =>*/}
            {/*                props.login()*/}
            {/*            }>Sign in with Google 🚀 </button>*/}
            {/*        </div>*/}
            {/*)}*/}
        </div>
    )
}