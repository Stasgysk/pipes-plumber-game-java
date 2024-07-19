import {useForm} from "react-hook-form";
import {Button, Form} from "react-bootstrap";

export default function RatingForm({onSendRating}) {
    const {register, handleSubmit, formState: {errors}} = useForm({mode: "onChange"});
    const onSubmit = data => {
        console.log(data);
        onSendRating(data.rating);
    };

    return (
        <Form onSubmit={handleSubmit(onSubmit)}>
            <Form.Group className="mb-3" controlId="ratingForm">
                <Form.Label>Rating</Form.Label>
                <input className="form-control"
                       type="number"
                       {...register("rating", {
                           min: {value: 0, message: "Rating is too small!"},
                           max: {value: 10, message: "Rating is too big!"},
                           required: {value: true, message: "Rating required!"}
                       })}
                       placeholder="Enter your rating here."/>
                <Form.Text className="text-muted">
                    {errors.rating?.message}
                </Form.Text>
            </Form.Group>
            <Button variant="primary" type="submit">
                Send rating
            </Button>
        </Form>
    );
}