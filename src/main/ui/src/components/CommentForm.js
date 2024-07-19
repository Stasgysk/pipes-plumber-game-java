import {useForm} from "react-hook-form";
import {Button, Form} from "react-bootstrap";

export default function CommentForm({onSendComment}) {
    const {register, handleSubmit, formState: {errors}} = useForm({mode: "onChange"});
    const onSubmit = data => {
        console.log(data);
        onSendComment(data.comment);
    };

    return (
        <Form onSubmit={handleSubmit(onSubmit)}>
            <Form.Group className="mb-3" controlId="commentForm">
                <Form.Label>Comment</Form.Label>
                <input className="form-control"
                       type="text"
                       {...register("comment", {
                           minLength: {value: 3, message: "Comment is too short!"},
                           maxLength: {value: 100, message: "Comment is too long!"},
                           required: {value: true, message: "Comment is required!"}
                       })}
                       placeholder="Enter your comment here."/>
                <Form.Text className="text-muted">
                    {errors.comment?.message}
                </Form.Text>
            </Form.Group>
            <Button variant="primary" type="submit">
                Send comment
            </Button>
        </Form>
    );
}