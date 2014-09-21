package com.cardfight.client;

import com.google.gwt.user.client.rpc.RemoteService;
import java.util.ArrayList;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("CardFightService")
public interface CardFightService extends RemoteService {
	  public CardFightSummary calculate(ArrayList<String> playersHands, String board, String discard);
}
