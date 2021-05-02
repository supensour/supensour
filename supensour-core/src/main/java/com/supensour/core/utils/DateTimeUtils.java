package com.supensour.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;

/**
 * @author Suprayan Yapura
 * @since 0.1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtils {

  public static final String TZ_NAME_UTC = "UTC";
  public static final String TZ_NAME_JAKARTA = "Asia/Jakarta";
  public static final String TZ_NAME_MAKASSAR = "Asia/Makassar";
  public static final String TZ_NAME_JAYAPURA = "Asia/Jayapura";
  public static final String TZ_NAME_SINGAPORE = "Asia/Singapore";

  public static final Locale LOCALE_INDONESIA = new Locale("id", "ID");
  public static final Locale LOCALE_SINGAPORE = new Locale("en", "SG");

  public static final ZoneId ZI_UTC = ZoneId.of(TZ_NAME_UTC);
  public static final ZoneId ZI_JAKARTA = ZoneId.of(TZ_NAME_JAKARTA);
  public static final ZoneId ZI_MAKASSAR = ZoneId.of(TZ_NAME_MAKASSAR);
  public static final ZoneId ZI_JAYAPURA = ZoneId.of(TZ_NAME_JAYAPURA);
  public static final ZoneId ZI_SINGAPORE = ZoneId.of(TZ_NAME_SINGAPORE);

  public static final TimeZone TZ_UTC = TimeZone.getTimeZone(ZI_UTC);
  public static final TimeZone TZ_JAKARTA = TimeZone.getTimeZone(ZI_JAKARTA);
  public static final TimeZone TZ_MAKASSAR = TimeZone.getTimeZone(ZI_MAKASSAR);
  public static final TimeZone TZ_JAYAPURA = TimeZone.getTimeZone(ZI_JAYAPURA);
  public static final TimeZone TZ_SINGAPORE = TimeZone.getTimeZone(ZI_SINGAPORE);

  /**
   * Format date using {@link SimpleDateFormat} to string.
   *
   * @param format        date format based on {@link SimpleDateFormat}
   * @param milliseconds  the date (in milliseconds) to be formatted, system current date will be used if null is given
   * @param locale        locale option to format the date, system default locale will be used if null is given
   * @param timezone      timezone option to format the date, system default timezone will be used if null is given
   *
   * @throws NullPointerException       if format is null
   * @throws IllegalArgumentException   if the format is invalid
   * @return formatted date
   */
  public static String formatDate(String format, Long milliseconds, Locale locale, TimeZone timezone) {
    var date = Optional.ofNullable(milliseconds)
        .map(Date::new)
        .orElse(null);
    return formatDate(format, date, locale, timezone);
  }

  /**
   * Format date using {@link SimpleDateFormat} to string.
   *
   * @param format    date format based on {@link SimpleDateFormat}
   * @param date      the date to be formatted, system current date will be used if null is given
   * @param locale    locale option to format the date, system default locale will be used if null is given
   * @param timezone  timezone option to format the date, system default timezone will be used if null is given
   *
   * @throws NullPointerException       if format is null
   * @throws IllegalArgumentException   if the format is invalid
   * @return formatted date
   */
  public static String formatDate(String format, Date date, Locale locale, TimeZone timezone) {
    Objects.requireNonNull(format, "Format must not be null");
    date = Optional.ofNullable(date).orElseGet(DateTimeUtils::currentDate);
    locale = Optional.ofNullable(locale).orElseGet(Locale::getDefault);
    timezone = Optional.ofNullable(timezone).orElseGet(TimeZone::getDefault);
    var formatter = new SimpleDateFormat(format, locale);
    formatter.setTimeZone(timezone);
    return formatter.format(date);
  }

  /**
   * Format date using {@link SimpleDateFormat} to string using system default timezone.
   *
   * @param format        date format based on {@link SimpleDateFormat}
   * @param milliseconds  the date (in milliseconds) to be formatted, system current date will be used if null is given
   * @param locale        locale option to format the date, system default locale will be used if null is given
   *
   * @throws NullPointerException       if format is null
   * @throws IllegalArgumentException   if the format is invalid
   * @return formatted date
   */
  public static String formatDate(String format, Long milliseconds, Locale locale) {
    return formatDate(format, milliseconds, locale, null);
  }

  /**
   * Format date using {@link SimpleDateFormat} to string using system default timezone.
   *
   * @param format    date format based on {@link SimpleDateFormat}
   * @param date      the date to be formatted, system current date will be used if null is given
   * @param locale    locale option to format the date, system default locale will be used if null is given
   *
   * @throws NullPointerException       if format is null
   * @throws IllegalArgumentException   if the format is invalid
   * @return formatted date
   */
  public static String formatDate(String format, Date date, Locale locale) {
    return formatDate(format, date, locale, null);
  }

  /**
   * Format date using {@link SimpleDateFormat} to string using system default locale.
   *
   * @param format        date format based on {@link SimpleDateFormat}
   * @param milliseconds  the date (in milliseconds) to be formatted, system current date will be used if null is given
   * @param timezone      timezone option to format the date, system default timezone will be used if null is given
   *
   * @throws NullPointerException       if format is null
   * @throws IllegalArgumentException   if the format is invalid
   * @return formatted date
   */
  public static String formatDate(String format, Long milliseconds, TimeZone timezone) {
    return formatDate(format, milliseconds, null, timezone);
  }

  /**
   * Format date using {@link SimpleDateFormat} to string using system default locale.
   *
   * @param format    date format based on {@link SimpleDateFormat}
   * @param date      the date to be formatted, system current date will be used if null is given
   * @param timezone  timezone option to format the date, system default timezone will be used if null is given
   *
   * @throws NullPointerException       if format is null
   * @throws IllegalArgumentException   if the format is invalid
   * @return formatted date
   */
  public static String formatDate(String format, Date date, TimeZone timezone) {
    return formatDate(format, date, null, timezone);
  }

  /**
   * Format date using {@link SimpleDateFormat} to string using system default locale and timezone.
   *
   * @param format        date format based on {@link SimpleDateFormat}
   * @param milliseconds  the date (in milliseconds) to be formatted, system current date will be used if null is given
   *
   * @throws NullPointerException       if format is null
   * @throws IllegalArgumentException   if the format is invalid
   * @return formatted date
   */
  public static String formatDate(String format, Long milliseconds) {
    return formatDate(format, milliseconds, null, null);
  }
  /**
   * Format date using {@link SimpleDateFormat} to string using system default locale and timezone.
   *
   * @param format    date format based on {@link SimpleDateFormat}
   * @param date      the date to be formatted, system current date will be used if null is given
   *
   * @throws NullPointerException       if format is null
   * @throws IllegalArgumentException   if the format is invalid
   * @return formatted date
   */
  public static String formatDate(String format, Date date) {
    return formatDate(format, date, null, null);
  }

  /**
   * Get start of a day based on the date on the given timezone.
   *
   * @param timezone      the timezone option, system default timezone will be used if null is given
   * @param milliseconds  the date (in milliseconds) to be calculated, system current date will be used if null is given
   *
   * @return start of the day
   */
  public static Date getStartOfDay(TimeZone timezone, Long milliseconds) {
    timezone = Optional.ofNullable(timezone).orElseGet(TimeZone::getDefault);
    milliseconds = Optional.ofNullable(milliseconds).orElseGet(DateTimeUtils::currentMillis);
    var calendar = Calendar.getInstance(timezone);
    calendar.setTimeInMillis(milliseconds);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    return calendar.getTime();
  }

  /**
   * Get start of a day based on the date on the given timezone.
   *
   * @param timezone  the timezone option, system default timezone will be used if null is given
   * @param date      the date to be calculated, system current date will be used if null is given
   *
   * @return start of the day
   */
  public static Date getStartOfDay(TimeZone timezone, Date date) {
    Long millis = Optional.ofNullable(date)
        .map(Date::getTime)
        .orElse(null);
    return getStartOfDay(timezone, millis);
  }

  /**
   * Get start of a day for today based on the given timezone.
   *
   * @param timezone  the timezone option, system default timezone will be used if null is given
   *
   * @return start of the day for today
   */
  public static Date getStartOfDay(TimeZone timezone) {
    return getStartOfDay(timezone, (Long) null);
  }

  /**
   * Get start of a day based on the date on system default timezone.
   *
   * @param milliseconds  the date (in milliseconds) to be calculated, system current date will be used if null is given
   *
   * @return start of the day
   */
  public static Date getStartOfDay(Long milliseconds) {
    return getStartOfDay(null, milliseconds);
  }

  /**
   * Get start of a day based on the date on system default timezone.
   *
   * @param date  the date to be calculated, system current date will be used if null is given
   *
   * @return start of the day
   */
  public static Date getStartOfDay(Date date) {
    return getStartOfDay(null, date);
  }

  /**
   * Get start of a day for today on system default timezone.
   *
   * @return start of the day for today
   */
  public static Date getStartOfDay() {
    return getStartOfDay(null, (Long) null);
  }

  /**
   * Get end of a day based on the date on the given timezone.
   *
   * @param timezone      the timezone option, system default timezone will be used if null is given
   * @param milliseconds  the date (in milliseconds) to be calculated, system current date will be used if null is given
   *
   * @return end of the day
   */
  public static Date getEndOfDay(TimeZone timezone, Long milliseconds) {
    timezone = Optional.ofNullable(timezone).orElseGet(TimeZone::getDefault);
    milliseconds = Optional.ofNullable(milliseconds).orElseGet(DateTimeUtils::currentMillis);
    var calendar = Calendar.getInstance(timezone);
    calendar.setTimeInMillis(milliseconds);
    calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
    calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
    calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
    calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));
    return calendar.getTime();
  }

  /**
   * Get end of a day based on the date on the given timezone.
   *
   * @param timezone  the timezone option, system default timezone will be used if null is given
   * @param date      the date to be calculated, system current date will be used if null is given
   *
   * @return end of the day
   */
  public static Date getEndOfDay(TimeZone timezone, Date date) {
    Long millis = Optional.ofNullable(date)
        .map(Date::getTime)
        .orElse(null);
    return getEndOfDay(timezone, millis);
  }

  /**
   * Get end of a day for today based on the given timezone.
   *
   * @param timezone  the timezone option, system default timezone will be used if null is given
   *
   * @return end of the day for today
   */
  public static Date getEndOfDay(TimeZone timezone) {
    return getEndOfDay(timezone, (Long) null);
  }

  /**
   * Get end of a day based on the date on system default timezone.
   *
   * @param milliseconds  the date (in milliseconds) to be calculated, system current date will be used if null is given
   *
   * @return end of the day
   */
  public static Date getEndOfDay(Long milliseconds) {
    return getEndOfDay(null, milliseconds);
  }

  /**
   * Get end of a day based on the date on system default timezone.
   *
   * @param date  the date to be calculated, system current date will be used if null is given
   *
   * @return end of the day
   */
  public static Date getEndOfDay(Date date) {
    return getEndOfDay(null, date);
  }

  /**
   * Get end of a day for today on system default timezone.
   *
   * @return end of the day for today
   */
  public static Date getEndOfDay() {
    return getEndOfDay(null, (Long) null);
  }

  /**
   * Return a date instance of current system date.
   *
   * @return current date
   */
  public static Date currentDate() {
    return new Date();
  }

  /**
   * Return time of current system in millis.
   *
   * @return current date in millis
   */
  public static Long currentMillis() {
    return System.currentTimeMillis();
  }

}
