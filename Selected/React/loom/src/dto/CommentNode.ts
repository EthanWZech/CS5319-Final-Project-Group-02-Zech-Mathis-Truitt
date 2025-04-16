export interface CommentNode {
    id: number;

    publishDate: Date;

    upvotes: number;

    downvotes: number;

    username: string;

    text: string;

    image: string | null;

    parentCommentId: number | null;

    replies: CommentNode[];
}