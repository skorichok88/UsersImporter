package com.importer.domain;

import com.importer.exception.EmptyEmailException;
import com.importer.exception.IncorrectEmailFormatException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String lastname;
    private String firstname;
    private String fiscalCode;
    private String description;
    private LocalDateTime lastAccessDate;

    public static class Builder {
        private String email;
        private String lastname;
        private String firstname;
        private String fiscalCode;
        private String description;
        private String lastAccessDate;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder fiscalCode(String fiscalCode) {
            this.fiscalCode = fiscalCode;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder lastAccessDate(String lastAccessDate) {
            this.lastAccessDate = lastAccessDate;
            return this;
        }

        private void checkEmailFormat(String email) {
            String regex = "(?:[a-z0-9!$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                throw new IncorrectEmailFormatException();
            }
        }

        public User build() {
            User user = new User();

            if (null == this.email || this.email.isEmpty()) {
                throw new EmptyEmailException();
            } else {
                checkEmailFormat(this.email.toLowerCase());
                user.setEmail(this.email.toLowerCase());
            }

            user.setFirstname(this.firstname);
            user.setLastname(this.lastname);
            user.setFiscalCode(this.fiscalCode);
            user.setDescription(this.description);

            if (null != this.lastAccessDate) {
                LastAccessDate lastAccessDate = new LastAccessDate(this.lastAccessDate);
                user.setLastAccessDate(lastAccessDate.toLocalDateTime());
            }

            return user;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLastAccessDate(LocalDateTime lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public LocalDateTime getLastAccessDate() {
        return lastAccessDate;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public String getDescription() {
        return description;
    }
}
