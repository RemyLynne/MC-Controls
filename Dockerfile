FROM node:lts-slim AS spa
WORKDIR /WebContent
ENV PNPM_HOME="/pnpm"
ENV PATH="$PNPM_HOME:$PATH"
RUN corepack enable

COPY WebContent/package.json WebContent/pnpm-lock.yaml WebContent/pnpm-workspace.yaml ./
RUN --mount=type=cache,id=pnpm,target=/pnpm/store \
    pnpm install --frozen-lockfile

COPY WebContent/ ./
RUN npx nx run-many -t build


FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

COPY . .
COPY --from=spa /WebContent/dist WebContent/dist

WORKDIR "MC Controls"
RUN ./gradlew clean bootjar


FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build ["/workspace/'MC Controls'/bootstrap/build/libs/*.jar", "app.jar"]

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
CMD ["serve"]
