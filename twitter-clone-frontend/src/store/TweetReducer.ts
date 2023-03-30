import Poll from "../model/Poll";
import { apiCall } from "./ApiMiddleware";

export const postTweet = (text: string, poll?: Poll, media?: FileList) => {
  const blob = new Blob([JSON.stringify({ text })], {
    type: "application/json",
  });
  const data = new FormData();
  data.append("tweet", blob);
  if (media !== undefined) data.append("media", media[0]);
  if (poll !== undefined) {
    data.append(
      "poll",
      new Blob([JSON.stringify(poll)], {
        type: "application/json",
      })
    );
  }
  return apiCall({
    url: `http://localhost:8080/api/v1/tweets`,
    useJwtToken: true,
    method: "POST",
    body: data,
  });
};
