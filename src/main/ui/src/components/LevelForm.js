import Form from 'react-bootstrap/Form';
import {Button} from "react-bootstrap";
import {useForm} from "react-hook-form";
import "./Popup.css";

export default function LevelForm(props) {
    const {register, handleSubmit, formState: {errors}} = useForm({mode: "onChange"});
    const onSubmit = data => {
        props.fetchLevelData(data.gameMode, data.level, data.difficulty, true);
    }


    return (
        <div className="game-over-popup">
            <div className="popup-inner">
                <div className="game-over-text">
                    <div className="rating-comment-form">
                        <Form onSubmit={handleSubmit(onSubmit)}>
                            <Form.Group className="mb-3" controlId="levelForm">
                                <Form.Label>Level</Form.Label>
                                <div className="form-level">
                                <input className="form-control"
                                       type="text"
                                       {...register("level", {
                                           minLength: {value: 3, message: "Level name is too short!"},
                                           maxLength: {value: 100, message: "Level name is too long!"},
                                           required: {value: true, message: "Level name is required!"}
                                       })}
                                       placeholder="Enter your level name here."/>
                                <Form.Text className="text-muted">
                                    {errors.level?.message}
                                </Form.Text>
                                </div>
                                <div>
                                <select className="form-select"
                                        {...register("difficulty")}>
                                    <option value="EASY">Easy</option>
                                    <option value="MEDIUM">Medium</option>
                                    <option value="HARD">Hard</option>
                                </select>
                                </div>
                                <div className="checkbox">
                                    <label>
                                    <input className="form-checkbox"
                                           type="checkbox"
                                           {...register("gameMode",)}
                                    />Easy mode
                                    </label>
                                </div>
                                <div className="form-button">
                                    <Button variant="primary" type="submit">
                                        Submit level
                                    </Button>
                                </div>
                            </Form.Group>
                        </Form>
                    </div>
                </div>
            </div>
        </div>
    );
}