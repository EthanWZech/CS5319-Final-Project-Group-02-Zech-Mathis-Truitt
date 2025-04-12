export interface AddCommentRequest {
    threadId: number;

    parentCommentId: number | null;

    username: string;

    text: string;
    
    image: string | null;
}