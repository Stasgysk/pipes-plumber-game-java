import {useForm} from "react-hook-form";
import {Button, Form} from "react-bootstrap";
import "Popup.css"

export default function PlayerForm({onSendScore}) {
    const {register, handleSubmit, formState: {errors}} = useForm({mode: "onChange"});
    const onSubmit = data => {
        console.log(data);
        onSendScore(data.player);

    }

    return (
            <Form onSubmit={handleSubmit(onSubmit)}>
                <Form.Group className="mb-3" controlId="playerForm">
                    <Form.Label>Player</Form.Label>
                    <input className="form-control"
                           type="text"
                           {...register("player", {
                               minLength: {value: 3, message: "Nickname is too short!"},
                               maxLength: {value: 100, message: "Nickname is too long!"},
                               required: {value: true, message: "Nickname is required!"}
                           })}
                           placeholder="Enter your nickname here."/>
                    <Form.Text className="text-muted">
                        {errors.player?.message}
                    </Form.Text>
                </Form.Group>
                <Button variant="primary" type="submit">
                    Set nickname
                </Button>
            </Form>
    );
}