export interface AddThreadRequest {
    topic: string;

    username: string;

    title: string;

    text: string;

    image: string | null;
}