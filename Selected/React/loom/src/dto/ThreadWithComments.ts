import { CommentNode } from "./CommentNode";

export interface ThreadWithComments {
    id: number;

    publishDate: Date;

    upvotes: number;

    downvotes: number;

    topic: string;

    username: string;

    title: string;

    text: string;

    image: string | null;

    comments: CommentNode[];
}