# Dockerfile for creating an vue3 app with vite and volume persitent
 
# Set the base image
FROM node:lts
 
# Maintainer
LABEL maintainer="Loan"
 
# Set environment variables
ENV APP_DIR=/vue_app
ENV VUE_APP_API_URL=http://0.0.0.0:8000
 
# Create app directory
RUN mkdir -p $APP_DIR

# Copy the source code
COPY . $APP_DIR
 
WORKDIR $APP_DIR

# Install dependencies
RUN npm install
# Expose port
EXPOSE 8000