package com.blamejared.jeitweaker.common.command;

import com.blamejared.jeitweaker.common.api.command.JeiCommand;

record GenerativeCommand<T>(JeiCommand<T> command, int generation) {}
