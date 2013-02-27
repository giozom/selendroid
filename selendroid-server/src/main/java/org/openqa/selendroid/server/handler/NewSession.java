/*
 * Copyright 2012 selendroid committers.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.openqa.selendroid.server.handler;


import org.openqa.selendroid.server.RequestHandler;
import org.openqa.selendroid.server.Response;
import org.openqa.selendroid.server.exceptions.SelendroidException;
import org.openqa.selendroid.util.SelendroidLogger;
import org.webbitserver.HttpRequest;

import com.google.gson.JsonObject;

public class NewSession extends RequestHandler {

  public NewSession(HttpRequest request,String mappedUri) {
    super(request,mappedUri);
  }

  @Override
  public Response handle() {
    SelendroidLogger.log("new session command");
    JsonObject payload = getPayload();

    JsonObject desiredCapabilities = payload.getAsJsonObject("desiredCapabilities");
    desiredCapabilities.addProperty("version", "0.1");
    String sessionID = null;
    try {
      sessionID = getAndroidDriver().initializeSession(desiredCapabilities);
    } catch (SelendroidException e) {
      SelendroidLogger.logError("Error while creating new session: ", e);
      return new Response(null, 33, e);
    }
    return new Response(sessionID, 0, desiredCapabilities);
  }
}