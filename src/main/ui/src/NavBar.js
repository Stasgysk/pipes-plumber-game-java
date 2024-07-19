import { Link, useMatch, useResolvedPath } from "react-router-dom"
import {useEffect, useState} from "react";
export default function Navbar(props) {
    const [photo, setPhoto] = useState([]);

    useEffect(() => {
        if(props.profile.photoURL){
            setPhoto(props.profile.photoURL)
        }
    }, [photo])

    console.log(props.profile)
    return (
        <nav className="nav">
            <Link to="/" className="site-title">
                Plumber Game
            </Link>
            <ul>
                {props.profile?.displayName && (
                    <CustomLink to="/game">Game</CustomLink>
                )}
                <CustomLink to="/score">Score</CustomLink>
                <CustomLink to="/rating">Rating</CustomLink>
                <CustomLink to="/comment">Comment</CustomLink>
                <CustomLink to="/profile"> {props.profile?.photoURL && (<img src={props.profile.photoURL} alt="photo" className="profile-photo"/>)}
                    Profile</CustomLink>
            </ul>
        </nav>
    )
}

function CustomLink({ to, children, ...props }) {
    const resolvedPath = useResolvedPath(to)
    const isActive = useMatch({ path: resolvedPath.pathname, end: true })

    return (
        <li className={isActive ? "active" : ""}>
            <Link to={to} {...props}>
                {children}
            </Link>
        </li>
    )
}