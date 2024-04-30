export interface LoginResponse {
  sessionId: string;
  userId: number;
  loginTimeStamp: string;
  expirationTimestamp: string;
}
