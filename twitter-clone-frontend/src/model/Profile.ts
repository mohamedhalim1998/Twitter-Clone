export default interface Profile {
  username: string;
  fullname: string;
  email: string;
  password: string;
  birthDay: number;
  createdAt: number;
  bio?: string;
  location?: string;
  pinnedTweetId?: number;
  profileImageUrl?: string;
  coverImageUrl?: string;
  profileProtected: boolean;
  followers: number;
  following: number;
  tweets: number;
}
