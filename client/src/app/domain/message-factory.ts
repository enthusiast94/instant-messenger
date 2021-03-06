/**
 * Created by manasb on 23-12-2016.
 */

import { MessageType } from './message-type';
import { ChatMessage } from './chat-message';
import { User } from "./user";
import { MessageSource } from "./message-source";

export class MessageFactory {

  public static createConnectMessage(username: string): string {
    return JSON.stringify({
      messageType: MessageType[MessageType.CONNECT],
      data: username
    });
  }

  public static createChatMessage(user: User, messageText: string): string {
    const chatMessage = new ChatMessage(MessageSource[MessageSource.USER], user, messageText, Date.now());
    return JSON.stringify({
      messageType: MessageType[MessageType.CHAT_MESSAGE],
      data: chatMessage
    });
  }
}
