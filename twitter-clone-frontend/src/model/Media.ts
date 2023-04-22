export default interface Media {
    id: number;
    type: "IMAGE"|"VIDEO";
    url: string;
    duration: number;
    height: number;
    width: number;
  }


  
 export function isMedia(obj: any): obj is Media {
    const keys: (keyof Media)[] = ['id', 'type', 'url', 'duration', 'height', 'width'];
    for (const key of keys) {
      if (!(key in obj)) {
        return false;
      }
    }
    return true;
  }
  
  