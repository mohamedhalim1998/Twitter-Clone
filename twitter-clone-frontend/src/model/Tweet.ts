export default interface Tweet {
  id: number;
  text: string;
  editHistory: number[];
  attachment: Attachment;
  authorId: string;
  conversationId: number;
  createdDate: Date;
  replyToId: number;
  likes: number;
  replays: number;
  retweet: number;
}
interface Attachment {
  type: "MEDIA" | "POLL";
  id: number;
}
