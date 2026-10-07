package org.mtransit.parser.ca_montreal_amt_train;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mtransit.commons.CharUtils;
import org.mtransit.parser.DefaultAgencyTools;
import org.mtransit.parser.MTLog;
import org.mtransit.parser.gtfs.data.GStop;

public class MontrealAMTTrainAgencyTools extends DefaultAgencyTools {

	public static void main(@NotNull String[] args) {
		new MontrealAMTTrainAgencyTools().start(args);
	}

	@Override
	public boolean useStopCodeForStopId() {
		return false; // TODO true later when getStopCode() does not return empty or not used
	}

	@Override
	public int getStopId(@NotNull GStop gStop) {
		final String stopCode = gStop.getStopCode();
		if (CharUtils.isDigitsOnly(stopCode)) {
			return Integer.parseInt(stopCode); // use stop code as stop ID
		}
		final Integer convertedStopId = convertStopIdNotSupported(stopCode);
		if (convertedStopId != null) {
			return convertedStopId;
		}
		throw new MTLog.Fatal("Unexpected stop code '%s' for %s", stopCode, gStop.toStringPlus());
	}

	@Nullable
	@Override
	public Integer convertStopIdNotSupported(@NotNull String stopCode) {
		if (stopCode.startsWith("FA")) {
			return 61_000_000 + Integer.parseInt(stopCode.substring(2));
		}
		return super.convertStopIdNotSupported(stopCode);
	}
}
