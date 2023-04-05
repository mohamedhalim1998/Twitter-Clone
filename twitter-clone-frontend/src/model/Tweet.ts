import Poll from "./Poll";
import Media from "./Media";
import Profile from "./Profile";
export default interface Tweet {
  id: number;
  text: string;
  editHistory: number[];
  attachment?: Attachment;
  authorId: string;
  conversationId: number;
  createdDate: number;
  likes: number;
  replays: number;
  retweet: number;
  includes?: Includes;
  tweetRefrence?: TweetRefrence;
}
interface Attachment {
  type: AttachmentType;
  attachmentId: number;
}

type AttachmentType = "IMAGE" | "POLL" | "VIDEO"
interface TweetRefrence {
  refType: TweetReferenceType;
  refId: number;
}
type TweetReferenceType = "RETWEET" | "REPLAY"
interface Includes {
  users: Profile[];
  tweets: Tweet[];
  polls: Poll[];
  media: Media[];
}

