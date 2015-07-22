# SignatureApp
Third part of the project [*Voter-Ballot Self Verification*](http://www.cjgomez.cl).

Android app for the voter to sign the encryption showed by [BallotSelection](http://www.cjgomez.cl).

## Files
1. **MainActivity.java**:
2. **ConfigurationActivity.java**:
3. **SignatureActivity.java**:
4. **ShowSignatureActivity.java**:

### Minimum Requirements
### Hardware

### Apps installed


## How to Use
* Make sure you satisfy the minimum requirements described above.
* Install the .apk file, which can be downloaded from [here](http://www.cjgomez.cl).

### Configuration
* At the moment that the voter receive the private key of the signature, must open this app and select "Configure", then the voter must scan the QR-Code showed by VoterKeyGenerator.

### Signature Process
* When BallotSelection shows the encrypted ballot in a QR-Code, asks to the voter to sign it, so she has to use this app.
* The voter scans the QR-Code of the encryption.
* The app, using the private key recorded before, signs the encrypted ballot (in background).
* After the signing process, the app shows in a QR-Code the signature produced.
* The voter must show the QR-Code of the signature to BallotSelection, after this SignatureApp finishes and the voter must keep using BallotSelection.

