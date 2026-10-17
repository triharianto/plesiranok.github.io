const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_123";
const BOB_UID = "bob_456";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

test("Unauthenticated user: cannot read users or favorites", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).get());
  await assertFails(unauthDb.collection("users").doc(ALICE_UID).collection("favorites").doc("fav1").get());
});

test("Authenticated user: can create, read, and delete their own user profile", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const userRef = aliceDb.collection("users").doc(ALICE_UID);
  
  await assertSucceeds(userRef.set({
    id: ALICE_UID,
    email: "alice@example.com",
    displayName: "Alice",
    createdAt: new Date()
  }));

  await assertSucceeds(userRef.get());
});

test("Authenticated user: cannot read or write another user's profile", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobRef = aliceDb.collection("users").doc(BOB_UID);
  
  await assertFails(bobRef.set({
    id: BOB_UID,
    email: "bob@example.com",
    createdAt: new Date()
  }));

  await assertFails(bobRef.get());
});

test("Authenticated user: can add, read, and delete their own favorite place", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const favRef = aliceDb.collection("users").doc(ALICE_UID).collection("favorites").doc("candi_prambanan");
  
  await assertSucceeds(favRef.set({
    placeId: "candi_prambanan",
    name: "Candi Prambanan",
    category: "TOURISM",
    region: "JOGJA",
    address: "Jl. Raya Solo - Yogyakarta No.16",
    rating: 4.8,
    priceTag: "Rp 50.000",
    contactNumber: "+62 274 496401",
    savedAt: new Date()
  }));

  await assertSucceeds(favRef.get());
  await assertSucceeds(aliceDb.collection("users").doc(ALICE_UID).collection("favorites").get());
  await assertSucceeds(favRef.delete());
});

test("Authenticated user: cannot access another user's favorite places", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobFavRef = aliceDb.collection("users").doc(BOB_UID).collection("favorites").doc("candi_prambanan");
  
  await assertFails(bobFavRef.get());
  await assertFails(bobFavRef.set({
    placeId: "candi_prambanan",
    name: "Candi Prambanan",
    category: "TOURISM",
    region: "JOGJA",
    savedAt: new Date()
  }));
});
